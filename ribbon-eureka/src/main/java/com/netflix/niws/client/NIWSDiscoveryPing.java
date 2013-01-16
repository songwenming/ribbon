package com.netflix.niws.client;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.NFLoadBalancer;
import com.netflix.loadbalancer.Server;



/**
 * "Ping" Discovery Client
 * i.e. we dont do a real "ping". We just assume that the server is up if Discovery Client says so
 * @author stonse
 *
 */
public class NIWSDiscoveryPing extends AbstractNIWSLoadBalancerPing {
	
        String clientName;
        
		NFLoadBalancer lb = null; 
		

		public NIWSDiscoveryPing() {
			
		}
		
        public NIWSDiscoveryPing(String identifier) {
			this.clientName = identifier;
		}
		
		public NFLoadBalancer getLb() {
			return lb;
		}

		/**
		 * Non IPing interface method - only set this if you care about the "newServers Feature"
		 * @param lb
		 */
		public void setLb(NFLoadBalancer lb) {
			this.lb = lb;
		}

		public boolean isAlive(Server server) {
		    boolean isAlive = true;
		    if (server!=null && server instanceof DiscoveryEnabledServer){
	            DiscoveryEnabledServer dServer = (DiscoveryEnabledServer)server;	            
	            InstanceInfo instanceInfo = dServer.getInstanceInfo();
	            if (instanceInfo!=null){	                
	                InstanceStatus status = instanceInfo.getStatus();
	                if (status!=null){
	                    isAlive = status.equals(InstanceStatus.UP);
	                }
	            }
	        }
		    return isAlive;
		}

        @Override
        public void initWithNiwsConfig(
                NiwsClientConfig niwsClientConfig) {
           this.clientName = niwsClientConfig.getClientName();            
        }
		
}
