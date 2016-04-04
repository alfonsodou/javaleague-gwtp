	// Lista de comandos
	LinkedList<Command> comandos = new LinkedList<Command>();

	@Override
	public List<Command> execute(GameSituations sp) {
		// Limpia la lista de comandos
		comandos.clear();
		// Obtiene las posiciones de tus jugadores
		Position[] jugadores = sp.myPlayers();
		for (int i = 0; i < jugadores.length; i++) {
			// Ordena a cada jugador que se ubique segun la alineacion1
			comandos.add(new CommandMoveTo(i, alineacion1[i]));
		}
		// Si no saca el rival
		if (!sp.isRivalStarts()) {
			// Obtiene los datos de recuperacion del balon
			int[] recuperadores = sp.getRecoveryBall();
			// Si existe posibilidad de recuperar el balon
			if (recuperadores.length > 1) {
				// Obtiene las coordenadas del balon en el instante donde
				// se puede recuperar el balon
				double[] posRecuperacion = sp.getTrajectory(recuperadores[0]);
				// Recorre la lista de jugadores que pueden recuperar
				for (int i = 1; i < recuperadores.length; i++) {
					// Ordena a los jugadores recuperadores que se ubique
					// en la posicion de recuperacion
					comandos.add(new CommandMoveTo(
							recuperadores[i],
							new Position(posRecuperacion[0], posRecuperacion[1])));
				}
			}
		}
		// Instancia un generador aleatorio
		Random r = new Random();
		// Recorre la lista de mis jugadores que pueden rematar
		for (int i : sp.canKick()) {
			// Si el jugador es de indice 8 o 10
			if (i == 8 || i == 10) {
				// condicion aleatoria
				if (r.nextBoolean()) {
					// Ordena que debe rematar al centro del arco
					comandos.add(new CommandHitBall(i, Constants.centroArcoSup,
							1, 12 + r.nextInt(6)));
				} else if (r.nextBoolean()) {
					// Ordena que debe rematar al poste derecho
					comandos.add(new CommandHitBall(i,
							Constants.posteDerArcoSup, 1, 12 + r.nextInt(6)));
				} else {
					// Ordena que debe rematar al poste izquierdo
					comandos.add(new CommandHitBall(i,
							Constants.posteIzqArcoSup, 1, 12 + r.nextInt(6)));
				}
			} else {
				// inicia contador en cero
				int count = 0;
				int jugadorDestino;
				// Repetir mientras el jugador destino sea igual al jugador que
				// remata
				while (((jugadorDestino = r.nextInt(11)) == i
				// o mientras la coordenada y del jugador que remata
				// es mayor que la coordenada y del que recibe
						|| jugadores[i].getY() > jugadores[jugadorDestino]
								.getY())
						// Y mientras el contador es menor a 20
						&& count < 20) {
					// incrementa el contador
					count++;
				}
				// Si el receptor del balon es el que remata
				if (i == jugadorDestino) {
					while ((jugadorDestino = r.nextInt(jugadores.length)) == i) {
					}
				}
				// Ordena que el jugador que puede rematar que de un pase
				// al jugador destino
				comandos.add(new CommandHitBall(i, jugadores[jugadorDestino],
						1, r.nextInt(45)));
			}
		}
		// Retorna la lista de comandos
		return comandos;
	}
