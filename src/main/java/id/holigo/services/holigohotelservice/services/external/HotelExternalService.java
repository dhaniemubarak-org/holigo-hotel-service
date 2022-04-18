package id.holigo.services.holigohotelservice.services.external;

import id.holigo.services.common.model.HotelDtoForExternal;

public interface HotelExternalService {

    public HotelDtoForExternal getHotelByCityId(Integer cityId, int pageSize,
            int pageNumber);
}
