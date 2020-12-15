package ch.ethz.systems.netbench.ext.basic;

import ch.ethz.systems.netbench.core.Simulator;
import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.network.OutputPort;
import ch.ethz.systems.netbench.core.run.infrastructure.OutputPortGenerator;


public class EcnTailDropOutputPortPerLinkGenerator extends OutputPortGenerator {

    private final long maxQueueSizeBytes;
    private final long ecnThresholdKBytes;

    public EcnTailDropOutputPortPerLinkGenerator(long maxQueueSizeBytes, long ecnThresholdKBytes) {
        this.maxQueueSizeBytes = maxQueueSizeBytes;
        this.ecnThresholdKBytes = ecnThresholdKBytes;
	SimulationLogger.logInfo("Port", "ECN_TAIL_DROP(maxQueueSizeBytes=" + maxQueueSizeBytes + ", ecnThresholdKBytes=" + ecnThresholdKBytes + ")");
    }				     

    @Override
    public OutputPort generate(NetworkDevice ownNetworkDevice, NetworkDevice towardsNetworkDevice, Link link) {
	String maxQueueSizeBytes_defined_str = Simulator.getConfiguration().getPropertyofLinkOrFail(ownNetworkDevice.getIdentifier(), towardsNetworkDevice.getIdentifier(), "output_port_max_queue_size_bytes");
	String ecnThresholdKBytes_defined_str = Simulator.getConfiguration().getPropertyofLinkOrFail(ownNetworkDevice.getIdentifier(), towardsNetworkDevice.getIdentifier(), "output_port_ecn_threshold_k_bytes");
        if (maxQueueSizeBytes_defined_str != null){
	    System.out.println("Setting the queuesize to" + maxQueueSizeBytes_defined_str + " and the eck K to " + ecnThresholdKBytes_defined_str);
	    return new EcnTailDropOutputPort(ownNetworkDevice, towardsNetworkDevice, link, Long.valueOf(maxQueueSizeBytes_defined_str), Long.valueOf(ecnThresholdKBytes_defined_str));
        }else{
	    return new EcnTailDropOutputPort(ownNetworkDevice, towardsNetworkDevice, link, maxQueueSizeBytes, ecnThresholdKBytes);
	}
    }

}
