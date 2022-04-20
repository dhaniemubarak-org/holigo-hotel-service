package id.holigo.services.holigohotelservice.services;

import java.sql.Date;

public interface HotelCronService {

    void runningPullFromExternal(Date chekIn, Integer cityId);
}
