/* Copyright (C) 2017  Olga Yakovleva <yakovleva.o.v@gmail.com> */

/* This program is free software: you can redistribute it and/or modify */
/* it under the terms of the GNU Lesser General Public License as published by */
/* the Free Software Foundation, either version 3 of the License, or */
/* (at your option) any later version. */

/* This program is distributed in the hope that it will be useful, */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the */
/* GNU Lesser General Public License for more details. */

/* You should have received a copy of the GNU Lesser General Public License */
/* along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package com.github.olga_yakovleva.rhvoice.android;

import android.content.Context;
import android.util.Log;
import com.github.olga_yakovleva.rhvoice.RHVoiceException;
import com.github.olga_yakovleva.rhvoice.TTSEngine;
import com.github.olga_yakovleva.rhvoice.VoiceInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Data
{
    private static final String TAG="RHVoice.Data";
    private final static List<LanguagePack> languages=new ArrayList<LanguagePack>();
    private static final Map<String,LanguagePack> index=new HashMap<String,LanguagePack>();

    static
    {
        LanguagePack lang=new LanguagePack("Russian","rus",2,2,"https://www.dropbox.com/s/hejp3zwzrv7oegy/data.zip?dl=1");
        lang.addVoice(new VoicePack("Anna",lang,3,0,"https://www.dropbox.com/s/0irrntqes1irx6f/data.zip?dl=1"));
        lang.addVoice(new VoicePack("Aleksandr",lang,3,0,"https://www.dropbox.com/s/by0ss7m92i5q5cp/data.zip?dl=1"));
        addLanguage(lang);
        lang=new LanguagePack("English","eng",2,1,"https://www.dropbox.com/s/1lthlfseb2wdvs8/data.zip?dl=1");
        lang.addVoice(new VoicePack("Slt",lang,3,0,"https://www.dropbox.com/s/x7idxdtn9amjsid/data.zip?dl=1"));
        lang.addVoice(new VoicePack("Bdl",lang,3,0,"https://www.dropbox.com/s/cqhj7nmgtizm051/data.zip?dl=1"));
        addLanguage(lang);
}

    public static LanguagePack getLanguage(String name)
    {
        return index.get(name);
    }

    public static List<LanguagePack> getLanguages()
    {
        return languages;
}

    private static void addLanguage(LanguagePack language)
    {
        languages.add(language);
        index.put(language.getCode(),language);
}

    public static boolean sync(Context context,IDataSyncCallback callback)
    {
        boolean done=true;
        for(LanguagePack language: languages)
            {
                if(!language.sync(context,callback))
                    done=false;
}
        return done;
}

    public static List<String> getPaths(Context context)
    {
        List<String> paths=new ArrayList<String>();
        for(LanguagePack language: languages)
            {
                paths.addAll(language.getPaths(context));
}
        return paths;
}

    public static boolean isSyncRequired(Context context)
    {
        for(LanguagePack language: languages)
            {
                if(language.isSyncRequired(context))
                    return true;
}
        return false;
}

    public static List<VoiceInfo> getVoices(Context context)
    {
        TTSEngine engine=null;
        try
            {
                engine=new TTSEngine("",Config.getDir(context).getAbsolutePath(),getPaths(context),CoreLogger.instance);
                return engine.getVoices();
            }
        catch(RHVoiceException e)
            {
                if(BuildConfig.DEBUG)
                    Log.e(TAG,"Engine initialization failed",e);
                return Collections.<VoiceInfo>emptyList();
            }
        finally
            {
                if(engine!=null)
                    engine.shutdown();
}
}
}