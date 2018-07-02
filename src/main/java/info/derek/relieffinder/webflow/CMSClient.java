package info.derek.relieffinder.webflow;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "webflow-cms", url = "https://api.webflow.com/collections")
interface CMSClient {

    @RequestMapping(method = RequestMethod.GET,
            path = "/5b2eb8a6d4bf811cce0a2977/items",
            headers = {"accept-version=1.0.0",
                    "Authorization=Bearer 96503fa4fd5f1c22917075cd3a2e4654ce72c4384714bb84a35d65a91adbd5de"})
    PagedCMSList<ShiftCMS> getShifts();

    @RequestMapping(method = RequestMethod.POST,
            path = "/5b2eb8a6d4bf811cce0a2977/items",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            params = "live=true",
            headers = {"accept-version=1.0.0",
                    "Authorization=Bearer 96503fa4fd5f1c22917075cd3a2e4654ce72c4384714bb84a35d65a91adbd5de"})
    ShiftCMS postShift(CMSCreationRequest shiftCMS);
}
