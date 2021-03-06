package com.tinkerpop.blueprints.impls.orient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrientLightWeightEdgeTest {

  private OrientGraph graph;

  @Before
  public void before() {
    graph = new OrientGraph("memory:" + OrientLightWeightEdgeTest.class.getSimpleName());
  }

  @After
  public void after() {
    graph.drop();
  }

  @Test
  public void testLiteweightToEavyMigration() throws Exception {
    graph.setUseLightweightEdges(true);
    graph.setAutoScaleEdgeType(true);
    OrientVertex vertex = graph.addVertex(null);
    OrientVertex vertex2 = graph.addVertex(null);
    vertex.addEdge("friend", vertex2);
    Object id = vertex.getId();
    graph.commit();
    // This shouldn't be done, i do it for ensure that the data is reloaded from the db, because the issue was after reload.
    graph.getRawGraph().getLocalCache().clear();
    OrientVertex vertexPrev = graph.getVertex(id);
    OrientVertex vertex3 = graph.addVertex(null);
    vertexPrev.addEdge("friend", vertex3);
    graph.commit();
  }

}
