package com.mc.gaul.thepitcore.Command.SubCommand.Admin;

import com.mc.gaul.thepitcore.API.Trait.PlayNPC;
import com.mc.gaul.thepitcore.Command.SubCommand.IFcmd;
import com.mc.gaul.thepitcore.File.ConfigX;
import com.mc.gaul.thepitcore.ThePitLite;
import com.mc.gaul.thepitcore.Utils.DecentHolo;
import com.mc.gaul.thepitcore.Utils.MessageUtil;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.IOException;


public class CreateNPC extends IFcmd {

    DecentHolo decentHolo;

    public CreateNPC() {
        super("createNPC", "<Play/Stats/Quests/Upgrade/Items/Prestige>");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot use this command!");
            return;
        }
        Player player = (Player) sender;
        if(args.length < 3) {
            player.sendMessage(MessageUtil.Preifx + " <Play/Stats/Quests/Upgrade/Items/Prestige>");
            return;
        }
        switch (args[2].toLowerCase()) {
            case "play":
                createPlayNPC(player, args[2].toUpperCase(), args[2].toUpperCase());
                break;
            case "stats":
                createStatsNPC(player, args[2].toUpperCase(), args[2].toUpperCase());
                break;
            case "quest":
                createQuestsNPC(player, args[2].toUpperCase(), args[2].toUpperCase());
                break;
            case "upgrade":
                createUpgradeNPC(player, args[2].toUpperCase(), args[2].toUpperCase());
                break;
            case "items":
                createItemsNPC(player, args[2].toUpperCase(), args[2].toUpperCase());
                break;
            case "prestige":
                createPrestigeNPC(player, args[2].toUpperCase(), args[2].toUpperCase());
        }

        player.sendMessage(ChatColor.GREEN + "Created " + args[2].toUpperCase() + " NPC.");
    }


    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @FunctionalInterface
    interface NpcCustomizer {
        void customize(NPC npc, DecentHolo holo, FileConfiguration config, CommandSender sender);
    }

    // 2. 공통 NPC 생성 메서드
    private void createCustomNPC(Player player, String npctype, String type,
                                 String skinName, String skinSignature, String skinTexture,
                                 NpcCustomizer customizer) {
        FileConfiguration config = new ConfigX(ThePitLite.getMain()).getConfig("npc");
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, type);
        String id = String.valueOf(npc.getId()) + "i";
        config.set("npc." + npctype + ".id." + id, npc.getId());
        config.set("npc." + npctype + ".id." + id + ".type", "PLAYER");
        config.set("npc." + npctype + ".id." + id + ".location.name", player.getWorld().getName());
        config.set("npc." + npctype + ".id." + id + ".location.x", player.getLocation().getX());
        config.set("npc." + npctype + ".id." + id + ".location.y", player.getLocation().getY());
        config.set("npc." + npctype + ".id." + id + ".location.z", player.getLocation().getZ());
        try{
            config.save(new ConfigX(ThePitLite.getMain()).getFile("npc"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        npc.getOrAddTrait(LookClose.class).setRange(4.0);
        npc.getOrAddTrait(LookClose.class).lookClose(true);
        npc.addTrait(PlayNPC.class);
        npc.getOrAddTrait(SkinTrait.class).setSkinPersistent(skinName, skinSignature, skinTexture);
        npc.setName("");
        npc.spawn(player.getLocation());
        DecentHolo decentHolo = new DecentHolo(npctype + npc.getId());
        decentHolo.createHologram(npc.getStoredLocation().add(0, 2.9, 0));

        // 타입별 커스텀 작업 실행
        customizer.customize(npc, decentHolo, config, player);

        player.sendMessage("Created " + npc.getName() + " NPC.");
    }

    private void createPlayNPC(Player player, String npctype, String type) {
        createCustomNPC(player, npctype, type,
                "test",
                "k86lQIvzft6ruTYLUyIBoKdAKvaN5hYSIHS6K7EXOyk5Ha3NXBCsvORF3nTGwqVuK4Vj2QgEUKGNtAl0TSavh6wQ7iQXeo9QJDO3j9iYVXBrXHOzV/5Fw5Fmel3x9gFqAbDpksjB2+6MBABwzXwJi3Q+Zo2NDith8h7yx+CTMpr7WSqGuBc0n04sTSE76yJftv0hSG9+qVV2Cy+8xeejurK7vOeEJqHkpjjGgKpzW0xFC7sn3CDLGRi3zmL8M2i+hPvborttW+oXg0ORqIiKNAHJOxJ5t7W65u6iIhoAhges/5jpAC9p9qtJYco3xdeYcvp5u0VnxXYnUfMnLx4Cv3LPbwpRx2JHVaT+RcJLCx9ExZqOFt02XiTwHVXc1tpg8bLIjD+0MPBb+jx5Fn1yTDqtihucayStcLqg5/TW2YdxUjbEZ1WkMvcxKOGrs3r0lNEPH71/K4qPUcdlXOmqnjNbSVWqvfm35+DT5KBHZPzklSeyK9/LKWj3uDLb9j7K6TQDzEtnEF9TpKuXi+eT4ZOa8ykFBeXXFeU45aIG/AuT6Fv29WkZ6baG3Wr5p+GW3GLtgf4SLf9jzeKu8iwt8NHG/9AA9Cw2dxWrywq2GnUVZEf5jhxrnmSBz6736FE96nJJyb42ISsWwed+QU7+RShsm/3tzhqHVkFXvKErFG0=",
                "ewogICJ0aW1lc3RhbXAiIDogMTc0NDIxMzQ3NjEyOCwKICAicHJvZmlsZUlkIiA6ICI1MTAwZGZmZDI0NDI0M2I0OGQxMmVkZTVkMjgxMzk2ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJFbGVjdHJvbmljU2V4IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2UzMDg2OGFhOGM4MjY2ZDhjYWY2ZmIxZjVlNDJhYmI5MDBiZTdmYmM2MjdmMmM5NjFmYjYwNmEzMmQ5NGE0ZDAiCiAgICB9CiAgfQp9",
                (npc, holo, config, sender1) -> {
                    holo.writeHologram(MessageUtil.Message(config.getString("play.text1")));
                    holo.writeHologram(MessageUtil.Message(config.getString("play.text2").replaceAll("%ver%", ThePitLite.getMain().getPlugin().getDescription().getVersion())));
                    holo.writeHologram(MessageUtil.Message(config.getString("play.text3").replaceAll("%count%", "§e0")));
                }
        );
    }


    private void createQuestsNPC(Player player, String npctype, String type) {
        createCustomNPC(player, npctype, type, "test","Jf05BD3Blrtw85voRsJRNz4uPRrE5EYxgcBMQaagoQBWrgb8i+GNhN+0Unr6jJG7z0j87JbQ/dc7zEPltwkUuNaG0poBIdOpEVkPtrR0TksGGuMvVxedhAMw6fGcab4VmnCJtD+L/ba2jYhnRvcpPNnhdzpdWgSlOzyIOWYoVxu+5j+RHDR/z0D5az2JxBBtFMZ9l7f6UoOw52wyPOFnbTpw0DtED95Y7i3yNwasELWjihf9hq5uL7eEvn6jpqJC9mLHjXnet/TiFzpV/yMXyFjka0T0HIswKke0vs2ko3RIe6E0SKwnSF7C3SYL3M3zSHTACPoco9Lx5OzMUZT39LHbZNTMIywRqYXvrnaoUWyPk6OF+asiUhIOpbrBXJYu7qjn+YCLX3GR7h472zparWliz6VCLFW41EdjUVBAdU81mOtnwFiv4v/6ZAluyI4AW2prXC4eoD85vfnZAYd/dDewaRFgQoY1rY/Bn/Oyk8dTNlOUwk3odW6Wqk4o+8OoFpCunmCueSK+7pjyGaAodsgNb9FUmMQiHZI0TSyTMDsjhQ22GJIRMoO2xMGlolHIK3PCJD9L2GUlo0mFXgSC4iDDwPqrzso4t/oPpkGWROpYFoMBmu8KIWByjw++bTgHlqINZYgcz6IrLs5/4EsxDqVQcn7DvAtrDFPiR4nuuBs=" ,
                "ewogICJ0aW1lc3RhbXAiIDogMTYyMTQzNDM5MTA4NywKICAicHJvZmlsZUlkIiA6ICIwYWFjMWRlZjUwZmI0N2RjODNmOGU2Njk3MTg1ODRkZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJ0aGVhcGlpc2JhZCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mNDUyN2E5OTYzNTNhMWFjZjYwNDE1NzhmMGQ2MjRhNDIwNzQxYmVlYzllNWY0NzI3YTBiZWU2OWE4OThmY2ZkIgogICAgfQogIH0KfQ==",
                ((npc, holo, config, sender1) -> {
                    holo.writeHologram(MessageUtil.Message(config.getString("quest.text1")));
                    holo.writeHologram(MessageUtil.Message(config.getString("quest.text2")));
                }));
    }

    private void createStatsNPC(Player player, String npctype, String type) {
        createCustomNPC(
                player,
                npctype,
                type,
                "stats_skin",
                "stats_signature",
                "stats_texture",
                (npc, holo, config, sender1) -> {
                    holo.writeHologram(MessageUtil.Message(config.getString("stats.text1")));
                    holo.writeHologram(MessageUtil.Message(config.getString("stats.text2")));
                }
        );
    }

    private void createUpgradeNPC(Player player, String npctype, String type) {
        createCustomNPC(
                player,
                npctype,
                type,
                "upgrade_skin",
                "upgrade_signature",
                "upgrade_texture",
                (npc, holo, config, sender1) -> {
                    holo.writeHologram(MessageUtil.Message(config.getString("upgrade.text1")));
                    holo.writeHologram(MessageUtil.Message(config.getString("upgrade.text2")));
                }
        );
    }

    private void createItemsNPC(Player player, String npctype, String type) {
        createCustomNPC(
                player,
                npctype,
                type,
                "items_skin",
                "items_signature",
                "items_texture",
                (npc, holo, config, sender1) -> {
                    holo.writeHologram(MessageUtil.Message(config.getString("items.text1")));
                    holo.writeHologram(MessageUtil.Message(config.getString("items.text2")));
                }
        );
    }


    private void createPrestigeNPC(Player player, String npctype, String type) {
        createCustomNPC(
                player,
                npctype,
                type,
                "prestige_skin",
                "prestige_signature",
                "prestige_texture",
                (npc, holo, config, sender1) -> {
                    holo.writeHologram(MessageUtil.Message(config.getString("prestige.text1")));
                    holo.writeHologram(MessageUtil.Message(config.getString("prestige.text2")));
                }
        );
    }

}
