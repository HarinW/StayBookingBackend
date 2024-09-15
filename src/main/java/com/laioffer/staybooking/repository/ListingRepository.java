package com.laioffer.staybooking.repository;

import com.laioffer.staybooking.model.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ListingRepository extends JpaRepository<ListingEntity, Long> {

  List<ListingEntity> findAllByHostId(Long hostId);

  // Left Join: show all listings even if there is no booking
  // ST_DWithin: find all listings within a certain distance
  // find all listings that are not booked (b.listing_id IS NULL) between checkIn and checkOut
  @Query(value = """
           SELECT l.*
           FROM listings l
           LEFT JOIN bookings b
               ON l.id = b.listing_id
               AND b.check_in_date < :checkOut
               AND b.check_out_date > :checkIn
           WHERE ST_DWithin(CAST(l.location AS geography), CAST(ST_MakePoint(:lon, :lat) AS geography), :distance)
               AND l.guest_number >= :guestNum
               AND b.listing_id IS NULL;
           """, nativeQuery = true)
  List<ListingEntity> searchListings(double lat, double lon, double distance, LocalDate checkIn, LocalDate checkOut, int guestNum);
}
