package info.derek.relieffinder.webflow;

import lombok.Data;

import java.util.List;

@Data
class PagedCMSList<T> {
    private List<T> items;
    private int count;
    private int limit;
    private int offset;
    private int total;
}
