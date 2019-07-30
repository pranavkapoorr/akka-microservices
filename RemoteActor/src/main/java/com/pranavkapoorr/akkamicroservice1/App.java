package com.pranavkapoorr.akkamicroservice1;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        ActorSystem system = ActorSystem.create("mysystem1");
        ActorRef worker = system.actorOf(myActor.getProps(),"worker");
        System.out.println(worker.path());
        
    }
     public static class myActor extends AbstractActor {
    	public static Props getProps() {
    		return Props.create(myActor.class);
    	}
    	
    	
		@Override
		public Receive createReceive() {
			return new ReceiveBuilder()
					.match(String.class, s -> {
						System.out.print("message received: "+s);
						getSender().tell("heyaa I am here your worker", getSelf());
					})
					.build();
		}
    	
    }
}
