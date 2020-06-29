package nickConnectorSponge;

import com.google.inject.Inject;
import io.github.nucleuspowered.nucleus.api.NucleusAPI;
import io.github.nucleuspowered.nucleus.api.exceptions.NicknameException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.network.ChannelBinding;
import org.spongepowered.api.network.ChannelBuf;
import org.spongepowered.api.network.RawDataListener;
import org.spongepowered.api.network.RemoteConnection;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

public class BungeeCordRawDataListener implements RawDataListener {

    @Override
    public void handlePayload(ChannelBuf data, RemoteConnection connection, Platform.Type side) {

        String[] string = data.readUTF().split(", ");
        Task.Builder taskBuilder = Task.builder();

        //try {
        if (string.length > 1) {

            String str1 = string[0];
            String str2 = (string[1] + "&r").replace("&", "§");

            //System.out.println("Got Ping Successfully" + str2);
            if (Sponge.getGame().getPluginManager().isLoaded("nucleus")) {
                taskBuilder.execute(new Runnable() {
                    public void run() {
                        try {
                            if (NucleusAPI.getNicknameService().isPresent()) {
                                NucleusAPI.getNicknameService().get().setNickname(Sponge.getServer().getPlayer(str1).get(), Text.of(str2), true);
                            } else {
                                Sponge.getServer().getPlayer(str1).get().sendMessage(Text.of("No workings, No nickname service"));
                            }

                        } catch (NicknameException e) {
                            e.printStackTrace();
                        }
                    }
                }).submit(Sponge.getPluginManager().getPlugin("sponge_nick_connector").get().getInstance().get());

            }

        }

    }
}
