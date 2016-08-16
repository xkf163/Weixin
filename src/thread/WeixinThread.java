package thread;


import util.Verify;
import entity.AccessToken;
import entity.JSTicket;

public class WeixinThread implements Runnable{

	public static AccessToken accessToken;
	public static JSTicket jsTicket;
	
	@Override
	public void run() {
        while(true) 
        {
        	accessToken = Verify.getAccessToken(); // ��΢�ŷ�����ˢ��access_token
			if(accessToken != null){
			    System.out.println(">>>initTOKEN : "+accessToken.getAccess_token());
			    jsTicket = Verify.getJsTicket(accessToken.getAccess_token());
			    System.out.println(">>>initJSTicket : "+jsTicket.getJsTicket()+"\n");
			}else{
			    System.out.println(">>>get auth key failed!");
			}
             
            try{
                if(null != accessToken){
                    Thread.sleep((accessToken.getExpires_in() - 200) * 1000);    // ����7000��
                }else{
                    Thread.sleep(60 * 1000);    // ���access_tokenΪnull��60����ٻ�ȡ
                }
            }catch(InterruptedException e){
                try{
                    Thread.sleep(60 * 1000);
                }catch(InterruptedException e1){
                    e1.printStackTrace();
                }
            }
        }
	}

}
