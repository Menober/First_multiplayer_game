package Client;

import java.util.ArrayList;

/**
 * Stworzone przez Krzyśka dnia 2017-09-13.
 */
public class Chat {
    public ArrayList<String> global;
    KHandler handler;
        public Chat(KHandler handler){
        this.global=new ArrayList<>();
        this.handler=handler;
        }

}
