package com.pranavkapoorr.akkamicroservice;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;

public class App {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("mysystem");
        system.actorOf(LocalActor.getProps(),"LOcalWorker");

    }
    public static class LocalActor extends AbstractActor{
        final ActorSelection selection;
        public static Props getProps(){
            return Props.create(LocalActor.class);
        }
        public LocalActor() {
            this.selection = context().actorSelection("akka.tcp://mysystem1@127.0.0.1:40001/user/worker");
        }

        @Override
        public void preStart() throws Exception {
            selection.tell("hello my worker", getSelf());
        }

        public Receive createReceive() {
           return new ReceiveBuilder()
                   .match(String.class,s->{
                       System.out.println("from remote: "+s);
                   })
                   .build();
        }
    }
}
