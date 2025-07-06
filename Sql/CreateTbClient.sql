package jhomework.model;

/**
 * 代表客户的实体类，对应数据库中的 clients 表。
 * 
 * <p>该类包含客户的唯一ID、姓名、电话号码及所在位置。</p>
 * 
 * @author 你的名字
 * @version 1.0
 */
public class Client {

    private long cid;
    private String cname;
    private String phonenum;
    private String loc;

    /**
     * 构造一个新的客户对象。
     */
    public Client() {
        // 无参构造函数
    }

    /**
     * 构造一个新的客户对象，指定所有属性。
     *
     * @param cid 客户ID，主键
     * @param cname 客户姓名，不可为空
     * @param phonenum 客户电话号码，唯一且不可为空
     * @param loc 客户所在地，不可为空
     */
    public Client(long cid, String cname, String phonenum, String loc) {
        this.cid = cid;
        this.cname = cname;
        this.phonenum = phonenum;
        this.loc = loc;
    }

    /**
     * 获取客户ID。
     * 
     * @return 客户ID
     */
    public long getCid() {
        return cid;
    }

    /**
     * 设置客户ID。
     * 
     * @param cid 客户ID
     */
    public void setCid(long cid) {
        this.cid = cid;
    }

    /**
     * 获取客户姓名。
     * 
     * @return 客户姓名
     */
    public String getCname() {
        return cname;
    }

    /**
     * 设置客户姓名。
     * 
     * @param cname 客户姓名
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * 获取客户电话号码。
     * 
     * @return 电话号码
     */
    public String getPhonenum() {
        return phonenum;
    }

    /**
     * 设置客户电话号码。
     * 
     * @param phonenum 电话号码
     */
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    /**
     * 获取客户所在地。
     * 
     * @return 客户所在地
     */
    public String getLoc() {
        return loc;
    }

    /**
     * 设置客户所在地。
     * 
     * @param loc 客户所在地
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }
}
