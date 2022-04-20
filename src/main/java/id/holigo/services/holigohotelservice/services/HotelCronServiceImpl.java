package id.holigo.services.holigohotelservice.services;

import id.holigo.services.holigohotelservice.schedules.PullingHotelFromExternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class HotelCronServiceImpl implements HotelCronService{

    @Autowired
    private PullingHotelFromExternal pullingHotelFromExternal;

    @Override
    public void runningPullFromExternal(Date checkIn, Integer cityId){
        pullingHotelFromExternal.pulling(cityId);
    }
}
