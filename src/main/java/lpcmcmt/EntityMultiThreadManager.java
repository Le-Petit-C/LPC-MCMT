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
            ArrayList<Entity> list = chunkToList.get(entityChunks.get(ChunkPos.fromEntity(entity)));
            if(list == null) continue;
            list.add(entity);
        }
        iterateSeparations.sort(Comparator.comparingInt(ArrayList::size));
    }

    private record ChunkPos(int x, int z) {
        public static ChunkPos fromEntity(Entity entity) {
            return new ChunkPos(entity.getBlockX() >> 4, entity.getBlockZ() >> 4);
        }
        @Override public int hashCode() {
            return (x << 16) | (z & ((1 << 16) - 1));
        }
        @Override
        public boolean equals(Object o) {
            if (o instanceof ChunkPos pos)
                return x == pos.x && z == pos.z;
            return false;
        }
        public void setConnect(HashMap<ChunkPos, ChunkPos> map) {
            addConnect(map, new ChunkPos(x - 1, z));
            addConnect(map, new ChunkPos(x + 1, z));
            addConnect(map, new ChunkPos(x, z - 1));
            addConnect(map, new ChunkPos(x, z + 1));
        }

        private void addConnect(@NotNull HashMap<ChunkPos, ChunkPos> map,@NotNull ChunkPos pos) {
            if(!map.containsKey(this) || !map.containsKey(pos)) return;
            ChunkPos root = findRoot(map);
            if (root == null) return;
            ChunkPos newRoot = pos.findRoot(map);
            if (newRoot == null) return;
            if (!root.equals(newRoot)) map.put(root, newRoot);
        }
        private ChunkPos findRoot(@NotNull HashMap<ChunkPos, ChunkPos> map) {
            if (!map.get(this).equals(this))
                map.put(this, map.get(this).findRoot(map));
            return map.get(this);
        }
    }
}
