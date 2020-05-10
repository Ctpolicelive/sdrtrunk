package io.github.dsheirer.module.decode.dmr.message;

import io.github.dsheirer.bits.CorrectedBinaryMessage;
import io.github.dsheirer.module.decode.dmr.DMRSyncPattern;
import io.github.dsheirer.module.decode.dmr.message.data.DataMessage;
import io.github.dsheirer.module.decode.dmr.message.data.DataType;
import io.github.dsheirer.module.decode.dmr.message.data.IDLEMessage;
import io.github.dsheirer.module.decode.dmr.message.data.csbk.CSBKMessage;
import io.github.dsheirer.module.decode.dmr.message.data.lc.TerminatorWithLCMessage;
import io.github.dsheirer.module.decode.dmr.message.data.lc.VoiceLCHeaderMessage;
import io.github.dsheirer.module.decode.dmr.message.voice.VoiceAMessage;
import io.github.dsheirer.module.decode.dmr.message.voice.VoiceEMBMessage;
import io.github.dsheirer.module.decode.dmr.message.voice.VoiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DMRMessageFactory
{
    private final static Logger mLog = LoggerFactory.getLogger(io.github.dsheirer.module.decode.dmr.message.DMRMessageFactory.class);

    /**
     * Creates a P25 message for known message types.
     *
     * NOTE: TSBK and PDU messages are not processed by this factory class.
     *
     * @param dataUnitID that identifies the message type
     * @param timestamp of the message
     * @param message containing message bits and bit error count results from nid error detection and
     * correction
     * @return constructed message parser
     */
    public static DataMessage createDataMessage(DataType dataUnitID, DMRSyncPattern pattern, CorrectedBinaryMessage message,
                                                long timestamp, int timeslot)
    {
        switch(dataUnitID)
        {
            case CSBK:
                return new CSBKMessage(pattern, message, timestamp, timeslot);
            case VOICE_HEADER:
                return new VoiceLCHeaderMessage(pattern, message, timestamp, timeslot);
            case TLC:
                return new TerminatorWithLCMessage(pattern, message, timestamp, timeslot);
            case SLOT_IDLE:
            default:
                return new IDLEMessage(pattern, message, timestamp, timeslot);
        }
    }

    public static VoiceMessage createVoiceMessage(DMRSyncPattern syncPattern, CorrectedBinaryMessage message,
                                                  long timestamp, int timeslot)
    {
        switch(syncPattern)
        {
            case BASE_STATION_VOICE:
            case MOBILE_STATION_VOICE:
            case DIRECT_MODE_VOICE_TIMESLOT_1:
            case DIRECT_MODE_VOICE_TIMESLOT_2:
                return new VoiceAMessage(syncPattern, message, timestamp, timeslot);
            case VOICE_FRAME_B:
            case VOICE_FRAME_C:
            case VOICE_FRAME_D:
            case VOICE_FRAME_E:
            case VOICE_FRAME_F:
                return new VoiceEMBMessage(syncPattern, message, timestamp, timeslot);
            default:
                mLog.warn("Unrecognized voice sync pattern [" + syncPattern + "]");
        }

        return null;
    }
}

