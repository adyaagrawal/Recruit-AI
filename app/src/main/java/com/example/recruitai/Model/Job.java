package com.example.recruitai.Model;

import com.example.recruitai.User;

import java.util.List;

public class Job {
        String Jname;
        String Jphase;
        String Cuid;
        String Cname;
        String Jloc;
        String Jdoclink;
        String IQ;
        List<UserClass> Juser;

    public Job() {
    }

    public Job(String jname, String cuid, String cname, String jloc, String jdoclink, String jphase) {
        Jname = jname;
        Cuid = cuid;
        Cname = cname;
        Jloc = jloc;
        Jdoclink = jdoclink;
        Jphase=jphase;
    }

    public String getJphase() {
        return Jphase;
    }

    public void setJphase(String jphase) {
        Jphase = jphase;
    }

    public String getIQ() {
        return IQ;
    }

    public void setIQ(String IQ) {
        this.IQ = IQ;
    }

    public String getJname() {
        return Jname;
    }

    public void setJname(String jname) {
        Jname = jname;
    }

    public String getCuid() {
        return Cuid;
    }

    public void setCuid(String cuid) {
        Cuid = cuid;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getJloc() {
        return Jloc;
    }

    public void setJloc(String jloc) {
        Jloc = jloc;
    }

    public String getJdoclink() {
        return Jdoclink;
    }

    public void setJdoclink(String jdoclink) {
        Jdoclink = jdoclink;
    }

    public List<UserClass> getJuser() {
        return Juser;
    }

    public void setJuser(List<UserClass> juser) {
        Juser = juser;
    }
}
