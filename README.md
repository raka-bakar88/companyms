This is an Company ms application that is part of Distributed/Microservices architecture that provides a functionality to review a company based on a job. The containeraized versions of this applications is available in [my docker hub account ](https://hub.docker.com/u/rkabkr). It uses Docker and Kubernetes to create and maintain the Images, Pods etc. 

**Techstack**
- Backend Framework: Spring Boot (Java 17)
- Database: PostgreSQL
- Messaging System: RabbitMQ
- Service Discovery: Spring Eureka
- Orchestration & Scaling: Kubernetes
- Configuration Management: Spring Config Server
- Distributed Tracing: Zipkin
- Health Monitoring: Spring Actuator
- Service-to-Service Communication: OpenFeign
- ORM Framework: Java Persistence API (JPA)
- Utilities: Lombok, Model Mapper
- Build Tool: Maven


  **Architecture**

  ![alt text](https://github.com/raka-bakar88/jobms/blob/main/microservice%20architecture%20diagram.png)

This application follows a Microservices Architecture where each service operates independently with its own database and interacts with others through messaging and service discovery mechanisms.

Microservices Overview:

1. Review Microservice works in conjunction with Company and Job microservices.

2. Each microservice uses its own PostgreSQL database.

3. Communication between microservices is handled through RabbitMQ message queues.

4. Configuration data for each microservice is managed by the Spring Config Server. Before launching, each service fetches its configuration settings.

5. Spring Cloud Eureka Server is used for service discovery, registration, and health monitoring.

6. Zipkin monitors and traces the performance of all microservices.

**Database**

Each of the Microservice application uses its own PostgresSQL as the database. However, they are connected to each other. below is the Entity Relationship Diagram of the database
![alt text](https://github.com/raka-bakar88/jobms/blob/main/JobApp%20ER%20Diagram.png)

**JOB Endpoints**
![alt text](https://github.com/raka-bakar88/companyms/blob/main/company%20ms%20api%20list.png)

**Example Class**


  Below is an example of code from Class CompanyServiceImpl
   ````
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              ReviewClient reviewClient,
                              ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
        this.modelMapper = modelMapper;
    }

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CompanyDTO> findAll() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(company -> modelMapper.map(company, CompanyDTO.class))
                .toList();
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = modelMapper.map(companyDTO, Company.class);
        Company savedCompany = companyRepository.save(company);

        return modelMapper.map(savedCompany, CompanyDTO.class);
    }

    @Override
    public CompanyDTO getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company","Company Id",companyId)
                );

        return modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    public CompanyDTO updateCompanyRating(CompanyDTO companyDTO, Long companyId) {
        Company savedCompany = companyRepository.findById(companyId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company","Company Id",companyId)
                );
        savedCompany.setDescription(companyDTO.getDescription());
        savedCompany.setName(companyDTO.getName());
        companyRepository.save(savedCompany);
        return companyDTO;
    }

    @Override
    public String deleteCompanyById(Long companyId) {
        Company savedCompany = companyRepository.findById(companyId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company","Company Id",companyId)
                );
        companyRepository.delete(savedCompany);
        return "delete is successfull";
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company =companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company ID", "companyId", reviewMessage.getCompanyId()));
        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }
}


   ````


Link to other Microservices repository:
- [Reviewms](https://github.com/raka-bakar88/reviewms)
- [jobms](https://github.com/raka-bakar88/jobms)
- [Eureka Service Registration Server ms](https://github.com/raka-bakar88/eurekams)
- [Config Server ms](https://github.com/raka-bakar88/configserverms)
- [Api gateway ms](https://github.com/raka-bakar88/gatewayms)
