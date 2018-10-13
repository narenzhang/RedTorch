package xyz.redtorch.core.gateway;

import java.util.HashSet;

import org.joda.time.DateTime;

import xyz.redtorch.core.entity.Account;
import xyz.redtorch.core.entity.CancelOrderReq;
import xyz.redtorch.core.entity.Contract;
import xyz.redtorch.core.entity.OrderReq;
import xyz.redtorch.core.entity.Position;
import xyz.redtorch.core.entity.SubscribeReq;

/**
 * @author sun0x00@gmail.com
 */
public interface Gateway {
	/**
	 * 获取ID
	 * @return
	 */
	String getGatewayID();
	
	/**
	 * 获取显示名称
	 * @return
	 */
	String getGatewayDisplayName();
	
	/**
	 * 返回已经按格式拼接好的用于日志打印的字符串信息
	 * @return
	 */
	String getGatewayLogInfo();
	/**
	 * 订阅
	 * @param subscribeReq
	 */
	void subscribe(SubscribeReq subscribeReq);
	
	/**
	 * 退订
	 * @param rtSymbol
	 */
	void unSubscribe(String rtSymbol);
	
	/**
	 * 连接
	 */
	void connect();
	/**
	 * 关闭
	 */
	void close();
	/**
	 * 发单
	 * @param orderReq 
	 */
	String sendOrder(OrderReq orderReq);
	/**
	 * 撤单
	 */
	void cancelOrder(CancelOrderReq cancelOrderReq);
	/**
	 * 查询账户
	 */
	void queryAccount();
	/**
	 * 查询持仓
	 */
	void queryPosition();
	
	/**
	 * 发送持仓事件
	 * @param position
	 */
	void emitPositon(Position position);
	
	/**
	 * 发送账户事件
	 * @param account
	 */
	void emitAccount(Account account);
	
	/**
	 * 发送合约事件
	 * @param contract
	 */
	void emitContract(Contract contract);
	
	/**
	 * 发送Tick事件
	 * @param tick
	 */
	public void emitTick(String gatewayID, String symbol, String exchange, String rtSymbol, String tradingDay, String actionDay,
			String actionTime, DateTime dateTime, Integer status, double lastPrice, Integer lastVolume, Integer volume,
			double openInterest, long preOpenInterest, double preClosePrice, double preSettlePrice, double openPrice,
			double highPrice, double lowPrice, double upperLimit, double lowerLimit, double bidPrice1, double bidPrice2,
			double bidPrice3, double bidPrice4, double bidPrice5, double bidPrice6, double bidPrice7, double bidPrice8,
			double bidPrice9, double bidPrice10, double askPrice1, double askPrice2, double askPrice3, double askPrice4,
			double askPrice5, double askPrice6, double askPrice7, double askPrice8, double askPrice9, double askPrice10,
			int bidVolume1, int bidVolume2, int bidVolume3, int bidVolume4, int bidVolume5,
			int bidVolume6, int bidVolume7, int bidVolume8, int bidVolume9, int bidVolume10,
			int askVolume1, int askVolume2, int askVolume3, int askVolume4, int askVolume5,
			int askVolume6, int askVolume7, int askVolume8, int askVolume9, int askVolume10);

	/**
	 * 发送成交事件
	 * @param trade
	 */
	void emitTrade(String gatewayID, String symbol, String exchange, String rtSymbol, String tradeID, String rtTradeID,
			String orderID, String rtOrderID,String originalOrderID, String direction, String offset, double price, int volume,
			String tradingDay, String tradeDate, String tradeTime, DateTime dateTime);
	
	/**
	 * 发送委托事件
	 * @param order
	 */
	void emitOrder(String gatewayID, String symbol, String exchange, String rtSymbol, String orderID, String rtOrderID,
			String direction, String offset, double price, int totalVolume, int tradedVolume, String status,
			String tradingDay, String orderDate, String orderTime, String cancelTime, String activeTime,
			String updateTime, int frontID, int sessionID,String originalOrderID);
	
	/**
	 * 获取配置 
	 * @return
	 */
	GatewaySetting getGatewaySetting();

	/**
	 * 获取已经订阅的合约符号
	 * @return
	 */
	HashSet<String> getSubscribedSymbols();
	
	/**
	 * 返回接口状态
	 * @return
	 */
	boolean isConnected();
	
	
}
