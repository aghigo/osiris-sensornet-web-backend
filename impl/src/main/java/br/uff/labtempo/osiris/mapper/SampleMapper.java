package br.uff.labtempo.osiris.mapper;


import br.uff.labtempo.osiris.model.request.SampleRequest;
import br.uff.labtempo.osiris.model.response.SampleResponse;
import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import br.uff.labtempo.osiris.to.collector.SampleCoTo;
import br.uff.labtempo.osiris.to.collector.SensorCoTo;

/**
 * Class that maps and converts Sample classes
 * (SampleRequest to SampleCoTo)
 * @see SampleRequest Sample data sent by the client on POST/PUT HTTP requests
 * @see SampleCoTo Sample representation on SensorNet module (encapsulates and relates NetworkCoTo with CollectorCoTo and SensorCoTo)
 * @see SampleResponse Sample response data sent by the API to the Client
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
public class SampleMapper {

    /**
     * Converts a SampleRequest to a SampleCoTo
     * @see SampleCoTo
     * @see SampleRequest
     * @param sampleRequest
     * @return SampleCoTo
     */
    public static SampleCoTo requestToCoTo(SampleRequest sampleRequest) {
        NetworkCoTo networkCoTo = NetworkMapper.requestToCoTo(sampleRequest.getNetwork());
        CollectorCoTo collectorCoTo = CollectorMapper.requestToCoTo(sampleRequest.getCollector());
        SensorCoTo sensorCoTo = SensorMapper.requestToCoTo(sampleRequest.getSensor());

        SampleCoTo sampleCoTo = new SampleCoTo(networkCoTo, collectorCoTo, sensorCoTo);
        return sampleCoTo;
    }

    /**
     * Converts a SampleCoTo to a SampleResponse
     * @see SampleCoTo
     * @see SampleResponse
     * @param sampleCoTo
     * @return SampleResponse
     */
    public static SampleResponse coToToResponse(SampleCoTo sampleCoTo) {
        SampleResponse sampleResponse = SampleResponse.builder()
                .network(sampleCoTo.getNetwork())
                .collector(sampleCoTo.getCollector())
                .sensor(sampleCoTo.getSensor())
                .build();
        return sampleResponse;
    }
}
