package me.towdium.jecharacters;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@Mod.EventBusSubscriber(bus = MOD)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod(JustEnoughCharacters.MODID)
public class JustEnoughCharacters {
    public static final String MODID = "jecharacters";
    public static final String MODNAME = "Just Enough Characters";
    public static Logger logger = LogManager.getLogger(MODID);

    public JustEnoughCharacters() {
        JechConfig.register();
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
    }
}

