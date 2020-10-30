package co.carrd.andwhat5.sts.sponge.wondertrade.teslalibs.message;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.FormattingCodeTextSerializer;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Message {
    private String message;

    private Message(String message) {
        this.message = message;
    }

    public static Message of(String message) {
        return new Message(message);
    }

    public Message arg(String name, Object arg) {
        this.message = this.message.replace("<" + name + ">", String.valueOf(arg));
        return this;
    }

    public Message args(Object ... args) {
        for (int i = 1; i < args.length; i += 2) {
            this.arg(String.valueOf(args[i - 1]), args[i]);
        }
        return this;
    }

    public String toString() {
        return this.message;
    }

    public Text toText() {
        return Message.toText(this.message);
    }

    public static Text toText(String message) {
        return TextSerializers.FORMATTING_CODE.deserialize(message);
    }
}

