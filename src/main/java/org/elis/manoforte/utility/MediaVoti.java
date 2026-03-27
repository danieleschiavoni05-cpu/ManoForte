package org.elis.manoforte.utility;
import org.elis.manoforte.model.Recensione;
import java.util.List;

public class MediaVoti {
	public static double calcolaMedia(List<Recensione> recensioni) {
        if (recensioni == null || recensioni.isEmpty()) return 0.0;
        return recensioni.stream()
                         .mapToInt(Recensione::getVoto)
                         .average()
                         .orElse(0.0);
    }
}
