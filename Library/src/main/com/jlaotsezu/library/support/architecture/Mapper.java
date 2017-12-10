package main.com.jlaotsezu.library.support.architecture;

public interface Mapper<SOURCE, DES> {
    DES map(SOURCE source);
}
