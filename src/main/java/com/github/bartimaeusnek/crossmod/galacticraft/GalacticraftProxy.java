/*
 * Copyright (c) 2019 bartimaeusnek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.bartimaeusnek.crossmod.galacticraft;

import com.github.bartimaeusnek.crossmod.galacticraft.solarsystems.Ross128;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregtech.api.objects.GT_UO_DimensionList;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

import static com.github.bartimaeusnek.crossmod.galacticraft.planets.ross128.world.oregen.BW_WorldGenRoss128.init_OresRoss128;
import static com.github.bartimaeusnek.crossmod.galacticraft.planets.ross128.world.oregen.BW_WorldGenRoss128.init_undergroundFluidsRoss128;

public class GalacticraftProxy {
    public static GT_UO_DimensionList uo_dimensionList = new GT_UO_DimensionList();
    static Configuration gtConf;
    private GalacticraftProxy() {
    }

    public static void preInit(FMLPreInitializationEvent e) {
        if (FMLCommonHandler.instance().getSide().isServer() || FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            serverpreInit(e);
        } else {
            clientpreInit(e);
        }
        commonpreInit(e);
    }

    private static void serverpreInit(FMLPreInitializationEvent e) {

    }

    private static void clientpreInit(FMLPreInitializationEvent e) {
    }

    private static void commonpreInit(FMLPreInitializationEvent e) {
        gtConf = new Configuration(new File(new File(e.getModConfigurationDirectory(), "GregTech"), "GregTech.cfg"));
        uo_dimensionList.getConfig(gtConf, "undergroundfluid");
        init_undergroundFluidsRoss128();
        if (gtConf.hasChanged())
            gtConf.save();

        Configuration c = new Configuration(new File(e.getModConfigurationDirectory(), "bartworks.cfg"));
        Ross128.ross128ID = c.get("CrossMod Interactions", "DimID - Ross128b", -64, "The Dim ID for Ross128b").getInt(-64);
        Ross128.enabled = c.get("CrossMod Interactions", "Galacticraft - Activate Ross128 System", true, "If the Ross128 System should be activated").getBoolean(true);
        if (c.hasChanged())
            c.save();

        init_OresRoss128();
    }

    public static void init(FMLInitializationEvent e) {
        if (FMLCommonHandler.instance().getSide().isServer() || FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            serverInit(e);
        } else {
            clientInit(e);
        }
        commonInit(e);
    }

    private static void serverInit(FMLInitializationEvent e) {

    }

    private static void clientInit(FMLInitializationEvent e) {

    }

    private static void commonInit(FMLInitializationEvent e) {
        if (Ross128.enabled)
            Ross128.init();
    }
}