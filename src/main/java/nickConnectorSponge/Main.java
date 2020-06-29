/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nickConnectorSponge;

import com.google.inject.Inject;
import io.github.nucleuspowered.nucleus.api.NucleusAPI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.network.ChannelBinding;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

/**
 *
 * @author Yochi
 */
@Plugin(id = "sponge_nick_connector",
        name = "Nick Connector",
        version = "1.0",
        description = "this plugin allows to get the player's global nickname by yochiwarez")

public class Main {

    ChannelBinding.RawDataChannel channel;

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("Successfully running Nick connector!!!");

        channel = Sponge.getGame().getChannelRegistrar().createRawChannel(this, "yochi:nick");
        channel.addListener(Platform.Type.SERVER, new BungeeCordRawDataListener());

    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {

        //Task.Builder taskBuilder = Task.builder();

        try {
            Thread.sleep(100);

            // Another example
            channel.sendTo(event.getTargetEntity(), buf -> {
                buf.writeUTF("getNick");
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
