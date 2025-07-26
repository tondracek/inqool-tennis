package cz.tondracek.inqooltennis.common;

public interface SoftDeletable<T> {

    T withDeleted(boolean deletedValue);
}
