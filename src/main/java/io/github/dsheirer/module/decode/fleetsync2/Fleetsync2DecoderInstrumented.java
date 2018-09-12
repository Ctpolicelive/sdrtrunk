/*******************************************************************************
 * sdr-trunk
 * Copyright (C) 2014-2018 Dennis Sheirer
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by  the Free Software Foundation, either version 3 of the License, or  (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied
 * warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License  along with this program.
 * If not, see <http://www.gnu.org/licenses/>
 *
 ******************************************************************************/
package io.github.dsheirer.module.decode.fleetsync2;

import io.github.dsheirer.dsp.afsk.AFSK1200DecoderInstrumented;
import io.github.dsheirer.gui.instrument.chart.IInstrumentedAFSK1200Decoder;
import io.github.dsheirer.sample.buffer.ReusableFloatBuffer;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Instrumented version of the AFSK-1200 decoder for use with DemodulatorViewerFX
 */
public class Fleetsync2DecoderInstrumented extends Fleetsync2Decoder implements IInstrumentedAFSK1200Decoder
{
    public SimpleIntegerProperty bufferCount = new SimpleIntegerProperty();

    public Fleetsync2DecoderInstrumented()
    {
        super(new AFSK1200DecoderInstrumented(false), null);
    }

    @Override
    public SimpleIntegerProperty getBufferCountProperty()
    {
        return bufferCount;
    }

    public AFSK1200DecoderInstrumented getAFSK1200Decoder()
    {
        return (AFSK1200DecoderInstrumented)getDecoder();
    }

    @Override
    public void receive(ReusableFloatBuffer reusableFloatBuffer)
    {
        super.receive(reusableFloatBuffer);

        bufferCount.setValue(bufferCount.intValue() + 1);
    }
}