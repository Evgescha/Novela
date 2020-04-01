package com.novelasgame.novelas.service.Game;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novelasgame.novelas.entity.game.Char;
import com.novelasgame.novelas.service.FormatVariables;
import com.novelasgame.novelas.service.TypeResources;
import com.novelasgame.novelas.storage.StorageProperties;

@Service
public class СharacterService {
	@Autowired
	StorageProperties storageProps;

	public byte[] getImageByte(long gameId, Char chr) {
		try {
//			System.out.println("URL: "+storageProps.getLocation() + FormatVariables.SPLITTER + gameId+ FormatVariables.SPLITTER
//					+ TypeResources.CHARACTER_IMAGES + FormatVariables.SPLITTER + chr.getName()
//					+ FormatVariables.SPLITTER + chr.getBody() + FormatVariables.PNG);
//			System.out.println("StartfindImages");
			storageProps.setLocation();
			
			//body
			BufferedImage img1 = ImageIO.read(new File(storageProps.getLocation() + FormatVariables.SPLITTER + gameId+ FormatVariables.SPLITTER
					+ TypeResources.CHARACTER_IMAGES + FormatVariables.SPLITTER + chr.getName()
					+ FormatVariables.SPLITTER + chr.getBody() + FormatVariables.PNG));

//			System.out.println("URL: "+storageProps.getLocation() + FormatVariables.SPLITTER + gameId+ FormatVariables.SPLITTER
//					+ TypeResources.CHARACTER_IMAGES + FormatVariables.SPLITTER + chr.getName()
//					+ FormatVariables.SPLITTER + chr.getBody() + FormatVariables.PNG);
			//for all body
			BufferedImage im = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_ARGB);
			im.getGraphics().drawImage(img1, 0, 0, null);
			
			//dress
			if(!chr.getDress().equals("null")) {
			BufferedImage img2 = ImageIO.read(new File(storageProps.getLocation() + FormatVariables.SPLITTER + gameId+ FormatVariables.SPLITTER
					+ TypeResources.CHARACTER_IMAGES + FormatVariables.SPLITTER + chr.getName() + FormatVariables.SPLITTER
					+ chr.getDress() + FormatVariables.PNG));
			im.getGraphics().drawImage(img2, 0, 0, null);
			
			}
			//emotion
			BufferedImage img3 = ImageIO.read(new File(storageProps.getLocation() + FormatVariables.SPLITTER + gameId+ FormatVariables.SPLITTER
					+ TypeResources.CHARACTER_IMAGES + FormatVariables.SPLITTER + chr.getName() + FormatVariables.SPLITTER
					+ chr.getEmotion() + FormatVariables.PNG));
			im.getGraphics().drawImage(img3, 0, 0, null);

			


			//thing
			if (!chr.getThing().contains("null")) {
				BufferedImage img4 = ImageIO.read(new File(storageProps.getLocation() + FormatVariables.SPLITTER + gameId+ FormatVariables.SPLITTER
						+ TypeResources.CHARACTER_IMAGES + FormatVariables.SPLITTER + chr.getName() + FormatVariables.SPLITTER
						+ chr.getThing() + FormatVariables.PNG));
				im.getGraphics().drawImage(img4, 0, 0, null);
			}

			// Save as new image
			// String name = System.currentTimeMillis()+"";
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(im, "PNG", baos);
			byte[] bytes = baos.toByteArray();

			return bytes;
		} catch (Exception ex) {
			return null;
		}
	}

}
