/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chase Minden
 */
public class EmptyException extends RuntimeException {

    /**
     * EFFECTS: Default constructor.
     */
    public EmptyException() {
        super();
    }

    /**
     * EFFECTS: A constructor that takes exception info.
     */
    public EmptyException(String s) {
        super(s);
    }
}

