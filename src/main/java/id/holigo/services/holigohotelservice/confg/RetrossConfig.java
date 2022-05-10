package id.holigo.services.holigohotelservice.confg;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class RetrossConfig {

//    private String mmid = "retross_01";
//    private String rqid = "T35Hd6624jbadlA2hbfSFsg356gDPfgr6d4P1N02";

    private String mmid = "holigo";
    private String rqid = "H0LJSHRG3754875Y4698NKJWEF8UHIGO";

    public void setMmid(String mmid) {
        this.mmid = mmid;
    }

    public void setRqid(String rqid) {
        this.rqid = rqid;
    }

    public String getMmid() {
        return mmid;
    }

    public String getRqid() {
        return rqid;
    }
}
