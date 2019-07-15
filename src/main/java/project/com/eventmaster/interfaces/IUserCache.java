package project.com.eventmaster.interfaces;

import project.com.eventmaster.data.model.CurrentUser;

public interface IUserCache {
    CurrentUser getCachedCurrentUser();
    void setCachedCurrentUser(CurrentUser user);
}
