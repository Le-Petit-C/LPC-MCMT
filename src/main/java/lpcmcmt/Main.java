package lpcmcmt;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static lpcmcmt.Utils.MixinStatics.*;

public class Main implements ModInitializer{
	public static final String MOD_ID = "lpcmcmt";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override public void onInitialize() {
		ServerLifecycleEvents.SERVER_STOPPED.register(
				(server) -> getMultiThread(server).disable()
		);
		LOGGER.info("LPCMCMT");
	}
}