package com.kermitemperor.coldsweathands.networking.packet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kermitemperor.coldsweathands.config.ConfigHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.kermitemperor.coldsweathands.ColdSweatHands.LOGGER;

public class JSONPacket{
    private JsonObject jsonData;


    @SuppressWarnings("unused")
    public JSONPacket() {

    }

    public JSONPacket(JsonObject jsonData) {
        this.jsonData = jsonData;
    }

    public JSONPacket(FriendlyByteBuf buf) {
        int length = buf.readVarInt();

        //THE HELL IS THIS
        byte[] dataBytes = new byte[length];
        buf.readBytes(dataBytes);
        //DON'T TOUCH!!!!!

        String jsonString = new String(dataBytes);
        this.jsonData = JsonParser.parseString(jsonString).getAsJsonObject();
    }


    public void encode(FriendlyByteBuf buf) {
        byte[] jsonByteData = this.jsonData.toString().getBytes();
        buf.writeVarInt(jsonByteData.length);
        buf.writeBytes(jsonByteData);
    }




    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ConfigHandler.CONFIG = jsonData;

        LOGGER.info("JSON Config recieved from " + context.getDirection().getOriginationSide());

        return true;
    }
}
