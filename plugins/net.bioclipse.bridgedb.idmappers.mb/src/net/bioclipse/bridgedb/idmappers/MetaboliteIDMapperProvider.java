package net.bioclipse.bridgedb.idmappers;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;

import net.bioclipse.bridgedb.IIDMapperProvider;

import org.bridgedb.BridgeDb;
import org.bridgedb.IDMapper;

public class MetaboliteIDMapperProvider implements IIDMapperProvider {

	public MetaboliteIDMapperProvider() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		MetaboliteIDMapperProvider prov = new MetaboliteIDMapperProvider();
		System.out.println(prov.loadIDMapper());
	}
	
	public IDMapper loadIDMapper() {
		try {
			// dirty trick: use a temp file
			String dataFile = "metabolites_20150717.bridge.gz";
			File tmpFile = File.createTempFile(dataFile, ".bridge");
			System.out.println(tmpFile.getAbsolutePath());
			InputStream stream = new GZIPInputStream(
				MetaboliteIDMapperProvider.class.getResourceAsStream(
				    "/data/" + dataFile
				)
			);
			System.out.println("stream: " + stream);
			System.out.println("path: " + tmpFile.toPath());
			tmpFile.delete();
			Files.copy(stream, tmpFile.toPath());
			tmpFile.deleteOnExit();
			
			Class.forName ("org.bridgedb.rdb.IDMapperRdb");
			IDMapper gdb = BridgeDb.connect (
				"idmapper-pgdb:" + tmpFile.getAbsolutePath()
			);
			return gdb;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		return "Metabolite ID Mapping Database";
	}

}
