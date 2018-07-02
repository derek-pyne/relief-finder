package info.derek.relieffinder.webflow;

import info.derek.relieffinder.shift.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class CMSManager {

    @Autowired
    CMSClient cmsClient;

    @Value("${webflow.api-key}")
    private String webflowApiKey;

    PagedCMSList<ShiftCMS> getAllShifts() {
        return cmsClient.getShifts();
    }

    ShiftCMS postShift(Shift shift) {
        return cmsClient.postShift(new CMSCreationRequest(ShiftCMS.fromShift(shift)));
    }

}
