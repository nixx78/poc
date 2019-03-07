package lv.nixx.poc.events;

import static lv.nixx.poc.events.PositionSandbox.Tier.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import lombok.*;


public class PositionSandbox {

	private Application app = new Application();
	
	private PostingsModel postingModels;
	private PositionsModel positionModels;
	
	@Before 
	public void init() {
		postingModels = new PostingsModel(app);
		positionModels = new PositionsModel(app);
		app.setPostingModels(postingModels);
	}

	@Test
	public void eventSandbox() {

		postingModels.loadInitialPostings(Arrays.asList(
				new Posting("Pos1", BigDecimal.valueOf(10.00), Tier1),
				new Posting("Pos2", BigDecimal.valueOf(20.00), Tier2),
				new Posting("Pos3", BigDecimal.valueOf(30.00), Tier2),
				new Posting("Pos4", BigDecimal.valueOf(30.00), Tier3)
		));
		
		
		postingModels.processEvent(new PostingEvent("Pos1", BigDecimal.valueOf(21.01)));
		System.out.println("====================");
		postingModels.processEvent(new PostingEvent("Pos2", BigDecimal.valueOf(05.01)));
		System.out.println("====================");
		postingModels.processEvent(new PostingEvent("Pos3", BigDecimal.valueOf(100.00)));
	}
	
	@Setter
	class Application {
		
		PostingsModel postingModels = new PostingsModel(app);
		
		void postingChanged(Posting posting) {
			
			Map<Tier, BigDecimal> balanceByTier = postingModels.getBalanceByTier();
			BigDecimal totalBalance = balanceByTier.values().stream().reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));

			System.out.println("Changed Posting: " + posting);
			System.out.println("Balance by Tier (Posings): " + balanceByTier);
			
			System.out.println("Total balance: " + totalBalance);
		}
		
	}
	
	
	class PostingsModel {
		
		Map<String, Posting> postings = new HashMap<>();
		Application app;

		public PostingsModel(Application app) {
			this.app = app;
		}

		void loadInitialPostings(Collection<Posting> positions) {
			positions.forEach(p -> {
				this.postings.put(p.getPos(), p);
			});	
		}

		void processEvent(PostingEvent event) {
			String pos = event.getPos();
			BigDecimal balance = event.getBalance();
			
			Posting p = postings.computeIfPresent(pos, (k, v) -> { 
				v.setBalance(balance); 
				return v;
			});
			
			app.postingChanged(p);
		}
		
	
		Map<Tier, BigDecimal> getBalanceByTier() {
			return postings.values()
					.stream()
					.collect(
							Collectors.groupingBy(Posting::getTier, 
									Collectors.reducing(BigDecimal.ZERO, Posting::getBalance, BigDecimal::add)
							)
					);
		}
		
	}
	
	class PositionsModel {
		Map<String, Position> positions = new HashMap<>();
		Application app;

		public PositionsModel(Application app) {
			this.app = app;
		}

		void loadInitialPositions(Collection<Position> positions) {
			positions.forEach(p -> {
				this.positions.put(p.getPos(), p);
			});	
		}

		void processEvent(PositionEvent event) {
		}
		
		
	}
	
	
	@Data
	@ToString
	@EqualsAndHashCode(of = "pos")
	@AllArgsConstructor
	class PositionEvent {
		String pos;
		BigDecimal balance;
	}

	@Data
	@ToString
	@EqualsAndHashCode(of = "pos")
	@AllArgsConstructor
	class PostingEvent {
		String pos;
		BigDecimal balance;
	}

	@Data
	@ToString
	@EqualsAndHashCode(of = "pos")
	@AllArgsConstructor
	class Position {
		String pos;
		BigDecimal balance;
		Tier tier;
	}

	@Data
	@ToString
	@EqualsAndHashCode(of = "pos")
	@AllArgsConstructor
	class Posting {
		String pos;
		BigDecimal balance;
		Tier tier;
	}

	enum Tier {
		Tier1, Tier2, Tier3
	}

}
