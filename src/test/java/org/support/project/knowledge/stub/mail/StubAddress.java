package org.support.project.knowledge.stub.mail;

import javax.mail.Address;

public class StubAddress extends Address {
    private String address;
    
    public StubAddress(String address) {
        super();
        this.address = address;
    }


    @Override
    public String toString() {
        return address;
    }

    
    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals(Object address) {
        // TODO Auto-generated method stub
        return false;
    }

}
