package com.etbike.server.domain.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Board.class)
public class Board_ {
    public static volatile SingularAttribute<Board, String> title;
    public static volatile SingularAttribute<Board, String> content;
    public static volatile SingularAttribute<Board, BoardCategory> category;
    public static volatile SingularAttribute<Board, String> writer;
    public static volatile SingularAttribute<Board, String> shareType;
}
