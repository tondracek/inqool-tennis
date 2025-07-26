package cz.tondracek.inqooltennis.common.model;

public interface SoftDeletable<T> {

    T withDeleted(boolean deletedValue);
}
