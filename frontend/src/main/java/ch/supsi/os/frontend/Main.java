package ch.supsi.os.frontend;

import ch.supsi.os.backend.BackendController;

public class Main {

    static BackendController bc = new BackendController();

    public static void main(String[] args) {
        System.out.println(bc.hello());
    }
}
