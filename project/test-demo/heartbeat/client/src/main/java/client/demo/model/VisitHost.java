package client.demo.model;

public class VisitHost {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_visit_host.id
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_visit_host.ip
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_visit_host.status
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_visit_host.reject_times
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    private Integer rejectTimes;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_visit_host.id
     *
     * @return the value of t_visit_host.id
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_visit_host.id
     *
     * @param id the value for t_visit_host.id
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_visit_host.ip
     *
     * @return the value of t_visit_host.ip
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_visit_host.ip
     *
     * @param ip the value for t_visit_host.ip
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_visit_host.status
     *
     * @return the value of t_visit_host.status
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_visit_host.status
     *
     * @param status the value for t_visit_host.status
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_visit_host.reject_times
     *
     * @return the value of t_visit_host.reject_times
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    public Integer getRejectTimes() {
        return rejectTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_visit_host.reject_times
     *
     * @param rejectTimes the value for t_visit_host.reject_times
     *
     * @mbggenerated Thu Aug 26 11:15:21 CST 2021
     */
    public void setRejectTimes(Integer rejectTimes) {
        this.rejectTimes = rejectTimes;
    }
}