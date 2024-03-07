### Customer Relationship Management (CRM) System

The Customer Relationship Management (CRM) System is a comprehensive software solution designed to streamline interactions with customers, manage sales processes, and enhance overall customer satisfaction. It provides businesses with a centralized platform to track customer information, monitor sales opportunities, and improve communication with clients.



## ER diagram

![CRM_ER_diagram]()


# CRM System Entities and Relationships

## Entities

1. **Customer**: Represents individual customers or companies interacting with the CRM system.
2. **Contact**: Represents individuals associated with the customers.
3. **Opportunity**: Represents potential sales opportunities or deals.
4. **Task**: Represents tasks or activities associated with customers or opportunities.
5. **Product**: Represents products or services offered by the company.

## Relationships

- Each Customer can have multiple Contacts (one-to-many relationship).
- Each Customer can have multiple Opportunities (one-to-many relationship).
- Each Opportunity is associated with a single Customer (many-to-one relationship).
- Each Opportunity can have multiple Tasks (one-to-many relationship).
- Each Task is associated with a single Customer or Opportunity (many-to-one relationship).
- Products may be related to Opportunities (many-to-many relationship), indicating which products are associated with specific opportunities.
