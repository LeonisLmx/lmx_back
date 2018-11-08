package com.lmx.blog.model.result;

/**
 * @author 刘明新
 * @date 2018/11/8 下午5:04
 */
public class User {

    private Community community;
    private Integer collectedEntriesCount;
    private String company;
    private Integer followersCount;
    private Integer followeesCount;
    private String role;
    private Integer postedPostsCount;
    private Boolean isAuthor;
    private Integer postedEntriesCount;
    private Integer totalCommentsCount;
    private Long ngxCachedTime;
    private Boolean ngxCached;
    private Integer viewedEntriesCount;
    private String jobTitle;
    private Integer subscribedTagsCount;
    private Integer totalCollectionsCount;
    private String username;
    private String avatarLarge;
    private String objectId;

    public class Community{
        private Weibo weibo;
        private Wechat wechat;

        public class Weibo{
            private String selfDescription;
            private String uid;
            private String blogAddress;
            private String username;
            private String avatarLarge;

            public String getSelfDescription() {
                return selfDescription;
            }

            public void setSelfDescription(String selfDescription) {
                this.selfDescription = selfDescription;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getBlogAddress() {
                return blogAddress;
            }

            public void setBlogAddress(String blogAddress) {
                this.blogAddress = blogAddress;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAvatarLarge() {
                return avatarLarge;
            }

            public void setAvatarLarge(String avatarLarge) {
                this.avatarLarge = avatarLarge;
            }
        }

        public class Wechat{
            private String avatarLarge;

            public String getAvatarLarge() {
                return avatarLarge;
            }

            public void setAvatarLarge(String avatarLarge) {
                this.avatarLarge = avatarLarge;
            }
        }

        public Weibo getWeibo() {
            return weibo;
        }

        public void setWeibo(Weibo weibo) {
            this.weibo = weibo;
        }

        public Wechat getWechat() {
            return wechat;
        }

        public void setWechat(Wechat wechat) {
            this.wechat = wechat;
        }
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Integer getCollectedEntriesCount() {
        return collectedEntriesCount;
    }

    public void setCollectedEntriesCount(Integer collectedEntriesCount) {
        this.collectedEntriesCount = collectedEntriesCount;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFolloweesCount() {
        return followeesCount;
    }

    public void setFolloweesCount(Integer followeesCount) {
        this.followeesCount = followeesCount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getPostedPostsCount() {
        return postedPostsCount;
    }

    public void setPostedPostsCount(Integer postedPostsCount) {
        this.postedPostsCount = postedPostsCount;
    }

    public Boolean getAuthor() {
        return isAuthor;
    }

    public void setAuthor(Boolean author) {
        isAuthor = author;
    }

    public Integer getPostedEntriesCount() {
        return postedEntriesCount;
    }

    public void setPostedEntriesCount(Integer postedEntriesCount) {
        this.postedEntriesCount = postedEntriesCount;
    }

    public Integer getTotalCommentsCount() {
        return totalCommentsCount;
    }

    public void setTotalCommentsCount(Integer totalCommentsCount) {
        this.totalCommentsCount = totalCommentsCount;
    }

    public Long getNgxCachedTime() {
        return ngxCachedTime;
    }

    public void setNgxCachedTime(Long ngxCachedTime) {
        this.ngxCachedTime = ngxCachedTime;
    }

    public Boolean getNgxCached() {
        return ngxCached;
    }

    public void setNgxCached(Boolean ngxCached) {
        this.ngxCached = ngxCached;
    }

    public Integer getViewedEntriesCount() {
        return viewedEntriesCount;
    }

    public void setViewedEntriesCount(Integer viewedEntriesCount) {
        this.viewedEntriesCount = viewedEntriesCount;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getSubscribedTagsCount() {
        return subscribedTagsCount;
    }

    public void setSubscribedTagsCount(Integer subscribedTagsCount) {
        this.subscribedTagsCount = subscribedTagsCount;
    }

    public Integer getTotalCollectionsCount() {
        return totalCollectionsCount;
    }

    public void setTotalCollectionsCount(Integer totalCollectionsCount) {
        this.totalCollectionsCount = totalCollectionsCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarLarge() {
        return avatarLarge;
    }

    public void setAvatarLarge(String avatarLarge) {
        this.avatarLarge = avatarLarge;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
