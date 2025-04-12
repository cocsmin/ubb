using System.Collections.Generic;

namespace csharp_avalonia.repository;
public interface Repository<T, ID>
{
    T save(T entity);
    
   // T findById(int id);
    T findOne(ID id);
    IEnumerable<T> findAll();
    
 //   void deleteById(int id);
    
  //  T existsById(int id);
    
}