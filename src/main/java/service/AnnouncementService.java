package service;

import entity.Announcement;

import java.util.List;

public interface AnnouncementService extends PagingService {
    int getTotalRows(String condition);

    List<Announcement> findAllAnnouncementsPageable(int pageNow, int pageCount);
}
