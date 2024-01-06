package service;

import dao.AnnouncementDao;
import dao.AnnouncementDaoImpl;
import entity.Announcement;

import java.util.List;

public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementDao announcementDao = new AnnouncementDaoImpl();

    @Override
    public int getTotalRows(String condition) {
        return announcementDao.getTotalRows();
    }

    @Override
    public List<Announcement> findAllAnnouncementsPageable(int pageNow, int pageCount) {
        return announcementDao.findAllAnnouncementsPageable(pageNow, pageCount);
    }
}
