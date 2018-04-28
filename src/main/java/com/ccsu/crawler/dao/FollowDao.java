package com.ccsu.crawler.dao;

import com.ccsu.crawler.model.Follow;
import com.ccsu.crawler.model.Seed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowDao {
    private Connection connection;

    private static String INSERT_SQL = "insert into tb_follow(followers,following) values (?,?)";
    private static String SELECT_SQL = "select * from tb_follow where login = ?";
    //private static String UPDATE_SQL = "UPDATE tb_follow set state = 0 where id = ?";
    private static String SELECTDUPLICATE_SQL = "select * from tb_follow where followers = ? and following = ?";

    public FollowDao(){
        connection = MysqlConnect.getConnect();
    }

    public int insert(Follow follow){
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
            ps.setString(1,follow.getFollowers());
            ps.setString(2,follow.getFollowing());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int selectDuplicate(String followers,String following){
        try {
            PreparedStatement ps = connection.prepareStatement(SELECTDUPLICATE_SQL);
            ps.setString(1,followers);
            ps.setString(2,following);
            return ps.executeQuery().getRow();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Seed select(){
        ResultSet rs;
        Seed seed = null;
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
            rs = ps.executeQuery();
            while (rs.next()){
                seed = new Seed();
                seed.setId(rs.getInt(1));
                seed.setSeedlogin(rs.getString(2));
                seed.setState(rs.getInt(3));
                seed.setUpdated(rs.getDate(4));
            }
            return seed;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Seed selectByLogin(String login){
        ResultSet rs;
        Seed seed = null;
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_SQL);
            ps.setString(1,login);
            rs = ps.executeQuery();
            while (rs.next()){
                seed = new Seed();
                seed.setId(rs.getInt(1));
                seed.setSeedlogin(rs.getString(2));
                seed.setState(rs.getInt(3));
                seed.setUpdated(rs.getDate(4));
            }
            return seed;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
