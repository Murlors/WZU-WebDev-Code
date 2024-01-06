package dao;

import entity.Announcement;

import java.util.List;

public interface AnnouncementDao {
    int getTotalRows();

    List<Announcement> findAllAnnouncementsPageable(int pageNow, int pageCount);
}
