package io;

import gfx.SpriteLibrary;
import map.GameMap;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

//For saving/importing the map
public class MapIO {

    public static void save(GameMap map){
        final URL urlToResourcesFolder = MapIO.class.getResource("/");
        File mapsFolder = new File(urlToResourcesFolder.getFile() + "/maps/");

        if(!mapsFolder.exists()){
            mapsFolder.mkdir();
        }

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mapsFolder.toString() + "/map.rga"))){
            bufferedWriter.write(map.serialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameMap load(SpriteLibrary spriteLibrary){

        try(BufferedReader bufferedReader = new BufferedReader((new FileReader(MapIO.class.getResource("/maps/map.rga").getFile())))){
            GameMap map = new GameMap();

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append(line);
            }

            map.applySerializedData(stringBuilder.toString());
            map.reloadGraphics(spriteLibrary);

            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
