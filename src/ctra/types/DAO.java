package ctra.types;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.jinsim.response.ResultResponse;

import org.apache.log4j.Logger;

import ctra.tools.DbHelper;

public class DAO
{
	static Logger log = Logger.getLogger(DAO.class);
	
	public static final boolean isDebug=false;
	
	public static int getNextRaceId(int serverNum)
	{
		
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs=null;
		try {
			String queryString = "select max(race_id)+1 as nextid from LOCC_integral_change_log where server_num=?";
			conn = DbHelper.getConnection();
			stm = conn.prepareStatement(queryString);
			stm.setInt(1, serverNum);
			rs=stm.executeQuery();
			if(rs.next())
			{
				return rs.getInt("nextid");
			}

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			log.debug(e);
			
		} finally {
			try
			{
				if(stm!=null) stm.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	
	public static void save(Player player,ResultResponse rsp,ServerManager serverManager)
	{
		Connection conn = null;
		PreparedStatement stm = null;

//		byte        Size;                // 84
//        byte        Type;                // ISP_RES
//        byte        ReqI;                // Ϊ 0, �����ǻ�Ӧ TINY_RES
//        byte        PLID;                // ����PLID (0 = �����ڽ������ǰ���˳�)
//
//        char        UName[24];        // LFS ID
//        char        PName[24];        // ������
//        char        Plate[8];        // ���� - ĩβ��null
//        char        CName[4];        // skinǰ׺
//
//        unsigned        TTime;        // �ܺ�ʱ (ms)
//        unsigned        BTime;        // best lap (ms)
//
//        byte        SpA;
//        byte        NumStops;        // ͣվ����
//        byte        Confirm;        // ���ȷ�ϱ�־ : DQ �ȵ� - see below
//        byte        SpB;
//
//        word        LapsDone;        // ��ɵ�Ȧ��
//        word        Flags;                // ����״̬��־ : ���� �ȵ� - see below
//
//        byte        ResultNum;        // ���� (0 = win / 255 = ���ڽ������)
//        byte        NumRes;                // �������������� (��λ��, ���ֵ����������)
//        word        PSeconds;        // ��ʱ���� (�Ѿ������ܺ�ʱ)
		
		try {
			String queryString = "insert into LOCC_finish_race_log set " +
					"lfs_name=?," +
					"race_id=?," +
					"total_time=?," +
					"best_lap_time=?," +
					"num_stops=?," +
					"laps_done=?," +
					"flags=?," +
					"result_num=?," +
					"num_res=?," +
					"time=now(),"+
					"track=?,"+
					"car=?,"+
		 			"server_num=?,"+
					"confirm=?";
			conn = DbHelper.getConnection();
			stm = conn.prepareStatement(queryString);
			stm.setString(1, rsp.getUserName());
			stm.setInt(2, serverManager.getRaceId());
			stm.setInt(3, rsp.getTotalTime().getTime());
			stm.setInt(4, rsp.getBestLapTime().getTime());
			stm.setInt(5, rsp.getNumberPitStops());
			stm.setInt(6, rsp.getLapsDone());
			stm.setInt(7, rsp.getPlayerFlags());
			stm.setInt(8, rsp.getResultPosition());
			stm.setInt(9, rsp.getTotalResults());
			stm.setString(10, serverManager.getState().getTrack().getName());
			stm.setString(11, player.getCar());
			stm.setInt(12, serverManager.getServerNum());
			stm.setInt(13, rsp.getConfirmationFlags());
			
			log.debug(stm.toString());
			
			stm.execute();

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			log.debug("save",e);
		}  finally {
			try
			{
				if(stm!=null) stm.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public static void save(Player player)
	{
		if(isDebug)return;
		
		Connection conn = null;
		PreparedStatement stm = null;
		
		try {
			String queryString = "update dhq_members set rank=?,integral=? where plyname=?";
			conn = DbHelper.getConnection();
			stm = conn.prepareStatement(queryString);
			stm.setInt(1, player.getRank());
			stm.setInt(2, player.getIntegral());
			stm.setString(3, player.getLfsName());
			
			
			log.debug(stm.toString());
			stm.executeUpdate();
			
			

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			log.debug(e);
		} finally {
			try
			{
				if(stm!=null) stm.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	private static void insert(Player player) throws ClassNotFoundException, SQLException {
//		Connection conn = null;
//		PreparedStatement stm = null;
//
//		try {
//			String queryString = "insert into dhq_members set plyname=?,rank=?,integral=?,time=now()";
//			conn = DbHelper.getConnection();
//			stm = conn.prepareStatement(queryString);
//			stm.setString(1, player.getLfsName());
//			stm.setInt(2, player.getRank());
//			stm.setInt(3, player.getIntegral());
//			
//
//			log.debug(stm.toString());
//			
//			stm.execute();
//
//		} finally {
//			if(stm!=null) stm.close();
//		}
//	}
	
	public static void mount(Player player) throws ClassNotFoundException, SQLException
	{
		
		if(isDebug)return;
		
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs=null;
		try {
			String queryString = "select * from dhq_members where plyname=?";
			conn = DbHelper.getConnection();
			stm = conn.prepareStatement(queryString);
			stm.setString(1, player.getLfsName());
			
			log.debug(stm.toString());
			
			rs=stm.executeQuery();
			if(rs.next())
			{
				player.setIntegral(rs.getInt("integral"));
			}

		} finally {
			try
			{
				if(stm!=null) stm.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static void addIntegralLog(int serverNum,int raceId,Player player,String reason,int addIntegral)
	{
		
		if(isDebug)return;
		
		Connection conn = null;
		PreparedStatement stm = null;

		try {
			String queryString = "insert into LOCC_integral_change_log set server_num=?,lfs_name=?,race_id=?,reason=?,add_integral=?,integral=?,time=now()";
			conn = DbHelper.getConnection();
			stm = conn.prepareStatement(queryString);
			stm.setInt(1, serverNum);
			stm.setString(2, player.getLfsName());
			stm.setInt(3, raceId);
			stm.setString(4, reason);
			stm.setInt(5, addIntegral);
			stm.setInt(6, player.getIntegral());
	

			log.debug(stm.toString());
			
			stm.execute();

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			log.debug("addIntegralLog",e);
		}  finally {
			try
			{
				if(stm!=null) stm.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
