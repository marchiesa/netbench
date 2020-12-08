package ch.ethz.systems.netbench.ext.basic;

import ch.ethz.systems.netbench.core.Simulator;
import ch.ethz.systems.netbench.core.log.SimulationLogger;
import ch.ethz.systems.netbench.core.network.Link;
import ch.ethz.systems.netbench.core.network.NetworkDevice;
import ch.ethz.systems.netbench.core.run.infrastructure.LinkGenerator;

public class PerfectSimpleLinkPerLinkGenerator extends LinkGenerator {

    private final long delayNs;
    private final long bandwidthBitPerNs;

    public PerfectSimpleLinkPerLinkGenerator(long delayNs, long bandwidthBitPerNs) {
        this.delayNs = delayNs;
	this.bandwidthBitPerNs = bandwidthBitPerNs;
        SimulationLogger.logInfo("Link", "PERFECT_SIMPLE_LINK(delayNs=" + delayNs + ", bandwidthBitPerNs=" + bandwidthBitPerNs + ")");
    }

    @Override
    public Link generate(NetworkDevice fromNetworkDevice, NetworkDevice toNetworkDevice) {
	String bandwidthBitPerNs_defined_str = Simulator.getConfiguration().getPropertyofLinkOrFail(fromNetworkDevice.getIdentifier(), toNetworkDevice.getIdentifier(), "link_bandwidth_bit_per_ns");
        if (bandwidthBitPerNs_defined_str!=null){
            return new PerfectSimpleLink(delayNs, Long.valueOf(bandwidthBitPerNs_defined_str));
	}else{
            return new PerfectSimpleLink(delayNs, bandwidthBitPerNs);
	}

    }

}
