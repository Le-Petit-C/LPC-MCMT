package lpcmcmt;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class EntityMultiThreadManager implements Runnable{
    public static void iterateEntities(ServerMultiThread multiThread, Iterable<Entity> iterable, @NotNull Consumer<Entity> action){
        EntityMultiThreadManager manager = new EntityMultiThreadManager(iterable, action);
        multiThread.multiThreadRun(manager);
    }

    @Override public void run() {
        while(true){
            ArrayList<Entity> list;
            synchronized (iterateSeparations){
                if(iterateSeparations.isEmpty()) break;
                list = iterateSeparations.getLast();
                iterateSeparations.removeLast();
            }
            for(Entity entity : list)
                action.accept(entity);
        }
    }

    @NotNull private final ArrayList<@NotNull ArrayList<Entity>> iterateSeparations;
    @NotNull private final Consumer<Entity> action;
    private EntityMultiThreadManager(@NotNull Iterable<Entity> iterable, @NotNull Consumer<Entity> action){
        HashMap<ChunkPos, ChunkPos> entityChunks = new HashMap<>();
        this.action = action;
        for(Entity entity : iterable){
            ChunkPos pos = ChunkPos.fromEntity(entity);
            entityChunks.put(pos, pos);
        }
        for(ChunkPos pos : entityChunks.keySet())
            pos.setConnect(entityChunks);
        HashMap<ChunkPos, ArrayList<Entity>> chunkToList = new HashMap<>();
        iterateSeparations = new ArrayList<>();
        for(Map.Entry<ChunkPos, ChunkPos> pair : entityChunks.entrySet()){
            if(pair.getKey().equals(pair.getValue())){
                ArrayList<Entity> entities = new ArrayList<>();
                iterateSeparations.add(entities);
                chunkToList.put(pair.getValue(), entities);
            }
        }
        for(Entity entity : iterable){
            ArrayList<Entity> list = chunkToList.get(ChunkPos.fromEntity(entity).findRoot(entityChunks));
            list.add(entity);
        }
        iterateSeparations.sort(Comparator.comparingInt(ArrayList::size));
        //Main.LOGGER.info("Entity list list size: {}", iterateSeparations.size());
    }

    private record ChunkPos(int x, int z) {
        public static ChunkPos fromEntity(Entity entity) {
            return new ChunkPos(entity.getBlockX() >> 4, entity.getBlockZ() >> 4);
        }
        @Override public int hashCode() {
            return (x << 16) | (z & ((1 << 16) - 1));
        }
        @Override public boolean equals(Object o) {
            if (o instanceof ChunkPos pos)
                return x == pos.x && z == pos.z;
            return false;
        }
        public void setConnect(HashMap<ChunkPos, ChunkPos> map) {
            addConnect(map, new ChunkPos(x - 1, z - 1));
            addConnect(map, new ChunkPos(x, z - 1));
            addConnect(map, new ChunkPos(x + 1, z - 1));
            addConnect(map, new ChunkPos(x - 1, z));
        }

        private void addConnect(@NotNull HashMap<@NotNull ChunkPos, @NotNull ChunkPos> map, @NotNull ChunkPos pos) {
            if (!map.containsKey(pos)) return;
            if (!map.containsKey(this)) map.put(this, this);
            ChunkPos root = findRoot(map);
            ChunkPos newRoot = pos.findRoot(map);
            if (!root.equals(newRoot)) map.put(root, newRoot);
        }
        private ChunkPos findRoot(@NotNull HashMap<ChunkPos, ChunkPos> map) {
            ChunkPos pointer = map.get(this);
            if (!pointer.equals(this))
                map.put(this, pointer = pointer.findRoot(map));
            return pointer;
        }
    }
}
